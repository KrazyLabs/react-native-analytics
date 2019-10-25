require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = 'KRNAnalytics'
  s.version      = package['version']
  s.summary      = package['description']
  s.license      = package['license']

  s.authors      = 'Various'
  s.homepage     = package['homepage']
  s.platform     = :ios, "10.0"

  s.source       = { :git => "https://github.com/KrazyLabs/react-native-analytics.git", :tag => "v#{s.version}" }
  s.source_files  = "ios/**/*.{h,m}"

  s.dependency 'React'
  s.dependency 'Analytics', '3.6.9'
end